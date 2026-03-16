import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  
  loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  constructor() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      this.errorMessage = 'Por favor completa todos los campos correctamente';
      return;
    }

    this.isLoading = true;
    const credentials = this.loginForm.value;
    console.log('Enviando credenciales:', credentials);
    
    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login exitoso:', response);
        this.errorMessage = '';
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Error completo:', error);
        console.error('Status:', error.status);
        console.error('Response:', error.error);
        
        // Mostrar el mensaje de error específico del servidor
        if (error.error?.message) {
          this.errorMessage = error.error.message;
        } else if (error.error?.errors) {
          this.errorMessage = Object.values(error.error.errors).join(', ');
        } else {
          this.errorMessage = `Error ${error.status}: ${error.statusText || 'Solicitud fallida'}`;
        }
        this.isLoading = false;
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }
}