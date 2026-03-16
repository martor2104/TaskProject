import { Routes } from '@angular/router';
import { Login } from '../app/Components/login/login';
import { LayoutComponent } from './layout/layout';
import { protectedGuard } from './guards/protected.guard';

export const routes: Routes = [
    { path: 'login', component: Login },
    { 
        path: 'dashboard', 
        component: LayoutComponent,
        canActivate: [protectedGuard]
    },
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
];
