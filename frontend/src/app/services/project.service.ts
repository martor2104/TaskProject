import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../models/project.model';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private http = inject(HttpClient);
  private readonly API_URL = 'http://localhost:8080/api/projects';

  getProjects(): Observable<Project[]> {
    return this.http.get<Project[]>(this.API_URL);
  }

  getProject(id: number): Observable<Project> {
    return this.http.get<Project>(`${this.API_URL}/${id}`);
  }

  createProject(project: Omit<Project, 'id'>): Observable<Project> {
    return this.http.post<Project>(this.API_URL, project);
  }

  updateProject(id: number, project: Partial<Project>): Observable<Project> {
    return this.http.put<Project>(`${this.API_URL}/${id}`, project);
  }

  deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}