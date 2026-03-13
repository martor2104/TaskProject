import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/auth';

  // Estado de autenticación usando signals
  private _isAuthenticated = signal(false);
  private _token = signal<string | null>(null);

  // Getters para el estado
  isAuthenticated = this._isAuthenticated.asReadonly();
  token = this._token.asReadonly();

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/login`, credentials).pipe(
      tap(response => {
        this._token.set(response.token);
        this._isAuthenticated.set(true);
        // Guardar token en localStorage si es necesario
        localStorage.setItem('authToken', response.token);
      })
    );
  }

  register(userData: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/register`, userData).pipe(
      tap(response => {
        this._token.set(response.token);
        this._isAuthenticated.set(true);
        localStorage.setItem('authToken', response.token);
      })
    );
  }

  logout(): void {
    this._token.set(null);
    this._isAuthenticated.set(false);
    localStorage.removeItem('authToken');
  }

  // Método para inicializar el estado desde localStorage
  initializeAuth(): void {
    const token = localStorage.getItem('authToken');
    if (token) {
      this._token.set(token);
      this._isAuthenticated.set(true);
    }
  }
}