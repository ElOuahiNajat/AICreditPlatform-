import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClientService } from '../../../core/services/client.service';
import { Client } from '../../../models/client.model';
import { MatDialog } from '@angular/material/dialog';
import { ClientFormModalComponent } from '../client-form-modal/client-form-modal.component';

@Component({
  selector: 'app-list-clients',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './list-clients.component.html',
})
export class ListClientsComponent implements OnInit {

  clients: Client[] = [];
  searchEmail: string = '';

  page: number = 0;      // numéro de page courant
  size: number = 5;      // nombre d'éléments par page
  totalPages: number = 0;

  constructor(private clientService: ClientService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients() {
    this.clientService.getClients(this.page, this.size, 'id').subscribe({
      next: (response: any) => {
        this.clients = response.content;
        this.totalPages = response.totalPages;
      },
      error: (err) => console.error(err)
    });
  }

  openAddModal() {
    const dialogRef = this.dialog.open(ClientFormModalComponent, { width: '500px', data: {} });
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.clientService.createClient(result).subscribe(() => this.loadClients());
    });
  }

  editClient(client: Client) {
    const dialogRef = this.dialog.open(ClientFormModalComponent, { width: '500px', data: { client } });
    dialogRef.afterClosed().subscribe(result => {
      if (result) this.clientService.updateClient(client.id, result).subscribe(() => this.loadClients());
    });
  }

  deleteClient(id: number) {
    if (confirm('Voulez-vous vraiment supprimer ce client ?')) {
      this.clientService.deleteClient(id).subscribe(() => this.loadClients());
    }
  }

  activate(id: number) {
    this.clientService.activateClient(id).subscribe(() => this.loadClients());
  }

  deactivate(id: number) {
    this.clientService.deactivateClient(id).subscribe(() => this.loadClients());
  }

  searchClient() {
    if (!this.searchEmail) return this.loadClients();
    this.clientService.getClientByEmail(this.searchEmail).subscribe({
      next: (client: Client) => {
        this.clients = client ? [client] : [];
        this.totalPages = 1;
        this.page = 0;
      },
      error: () => this.clients = []
    });
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.loadClients();
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadClients();
    }
  }
}
