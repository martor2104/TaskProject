export interface User{
    id: number;
    username: string;
    email: string;
    systemRole: 'ADMIN' | 'USER';
}