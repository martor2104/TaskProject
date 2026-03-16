import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Verificar si está autenticado usando el signal
  if (authService.isAuthenticated() || localStorage.getItem('authToken')) {
    return true;
  }

  // Redirigir al login
  router.navigate(['/login']);
  return false;
};
