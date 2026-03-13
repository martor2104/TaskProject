export type Priority = 'HIGH' | 'MEDIUM' | 'LOW';
export type Status = 'TODO' | 'DOING' | 'DONE';

export interface Task {
  id: number;
  title: string;
  description: string;
  date: string;
  priority: Priority;
  status: Status;
  project: Project;
  assignedUser?: User;
}

import { Project } from './project.model';
import { User } from './user.model';