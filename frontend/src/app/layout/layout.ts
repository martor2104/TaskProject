import { Component, computed, signal, inject, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../services/task.service';
import { AuthService } from '../services/auth.service';
import { Task, Priority, Status } from '../models/task.model';

type FilterType = 'all' | 'pending' | 'done' | 'high';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './layout.html',
  styleUrl: './layout.scss',
  encapsulation: ViewEncapsulation.None 
})
export class LayoutComponent implements OnInit {
  private taskService = inject(TaskService);
  private authService = inject(AuthService);

  readonly TAG_COLORS: Record<string, string> = {
    personal: '#7F77DD',
    trabajo:  '#378ADD',
    hogar:    '#EF9F27',
    salud:    '#1D9E75',
  };

  readonly STATUS_MAP: Record<Status, string> = {
    'TODO':  'pendiente',
    'DOING': 'en curso',
    'DONE':  'hecha',
  };

  readonly PRIORITY_MAP: Record<Priority, string> = {
    'HIGH':   'high',
    'MEDIUM': 'med',
    'LOW':    'low',
  };

  readonly QUOTES = [
    '"Haz primero lo importante,\nno lo urgente."',
    '"Un paso pequeño\nes mejor que ninguno."',
    '"El orden es la base\nde todas las virtudes."',
    '"Empieza donde estás,\nusa lo que tienes."',
  ];

  tasks         = signal<Task[]>([]);
  loading       = signal(true);
  activeFilter  = signal<FilterType>('all');
  newTaskTitle  = signal('');
  quote         = '';
  dateStr       = '';

  // Stats computadas a partir de las tareas reales
  totalTasks   = computed(() => this.tasks().length);
  doneTasks    = computed(() => this.tasks().filter(t => t.status === 'DONE').length);
  pendingTasks = computed(() => this.tasks().filter(t => t.status !== 'DONE').length);
  urgentTasks  = computed(() => this.tasks().filter(t => t.priority === 'HIGH' && t.status !== 'DONE').length);
  progressPct  = computed(() =>
    this.totalTasks() ? Math.round((this.doneTasks() / this.totalTasks()) * 100) : 0
  );
  pendingBadge = computed(() => this.pendingTasks());

  filteredTasks = computed(() => {
    const filter = this.activeFilter();
    const list   = this.tasks();
    if (filter === 'pending') return list.filter(t => t.status !== 'DONE');
    if (filter === 'done')    return list.filter(t => t.status === 'DONE');
    if (filter === 'high')    return list.filter(t => t.priority === 'HIGH');
    return list;
  });

  pendingFiltered = computed(() => this.filteredTasks().filter(t => t.status !== 'DONE'));
  doneFiltered    = computed(() => this.filteredTasks().filter(t => t.status === 'DONE'));

  // Estadísticas por proyecto (equivale a "categorías")
  projectStats = computed(() => {
    const all      = this.tasks();
    const projects = [...new Set(all.map(t => t.project?.name).filter(Boolean))];
    const max      = Math.max(...projects.map(p => all.filter(t => t.project?.name === p).length), 1);
    const colors   = ['#7F77DD', '#378ADD', '#EF9F27', '#1D9E75', '#E24B4A'];
    return projects.slice(0, 5).map((p, i) => ({
      name:  p,
      count: all.filter(t => t.project?.name === p).length,
      pct:   Math.round((all.filter(t => t.project?.name === p).length / max) * 100),
      color: colors[i % colors.length],
    }));
  });

  ngOnInit(): void {
    const days   = ['domingo','lunes','martes','miércoles','jueves','viernes','sábado'];
    const months = ['enero','febrero','marzo','abril','mayo','junio','julio','agosto','septiembre','octubre','noviembre','diciembre'];
    const now    = new Date();
    this.dateStr = `${days[now.getDay()]} ${now.getDate()} ${months[now.getMonth()]}`;
    this.quote   = this.QUOTES[Math.floor(Math.random() * this.QUOTES.length)];

    this.loadTasks();
  }

  loadTasks(): void {
    this.loading.set(true);
    this.taskService.getTasks().subscribe({
      next: (tasks) => {
        this.tasks.set(tasks);
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  setFilter(filter: string): void {
    if (['all', 'pending', 'done', 'high'].includes(filter)) {
      this.activeFilter.set(filter as FilterType);
    }
  }

  toggleTask(task: Task): void {
    const newStatus: Status = task.status === 'DONE' ? 'TODO' : 'DONE';
    this.taskService.updateTask(task.id, { status: newStatus }).subscribe({
      next: (updated) => {
        this.tasks.update(list => list.map(t => t.id === updated.id ? updated : t));
      }
    });
  }

  deleteTask(id: number, event: Event): void {
    event.stopPropagation();
    this.taskService.deleteTask(id).subscribe({
      next: () => this.tasks.update(list => list.filter(t => t.id !== id)),
    });
  }

  addTask(event: KeyboardEvent): void {
    if (event.key !== 'Enter') return;
    const title = this.newTaskTitle().trim();
    if (!title) return;

    const newTask: Omit<Task, 'id'> = {
      title,
      description: '',
      date: new Date().toISOString().split('T')[0],
      priority: 'LOW',
      status: 'TODO',
      project: this.tasks()[0]?.project ?? ({ id: 0, name: '', description: '' } as any),
    };

    this.taskService.createTask(newTask).subscribe({
      next: (created) => {
        this.tasks.update(list => [...list, created]);
        this.newTaskTitle.set('');
      }
    });
  }

  clearDone(): void {
    const done = this.tasks().filter(t => t.status === 'DONE');
    done.forEach(t => {
      this.taskService.deleteTask(t.id).subscribe();
    });
    this.tasks.update(list => list.filter(t => t.status !== 'DONE'));
  }

  isChipActive(chip: string): boolean {
    return this.activeFilter() === chip;
  }

  priorityClass(priority: Priority): string {
    return this.PRIORITY_MAP[priority] ?? 'low';
  }

  quoteLines(): string[] {
    return this.quote.split('\n');
  }

  userInitial(): string {
    // El AuthService guarda el token pero no el nombre; usamos email del token si está
    const token = this.authService.token();
    if (!token) return 'U';
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return (payload.sub as string)?.[0]?.toUpperCase() ?? 'U';
    } catch { return 'U'; }
  }

  userEmail(): string {
    const token = this.authService.token();
    if (!token) return '';
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub ?? '';
    } catch { return ''; }
  }
}