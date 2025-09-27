// src/app/features/clients/client-form-modal/client-form-modal.component.ts
import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { Client } from '../../../models/client.model';

@Component({
  selector: 'app-client-form-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, MatDialogModule],
  templateUrl: './client-form-modal.component.html',
})
export class ClientFormModalComponent implements OnInit {
  form!: FormGroup;
  isEdit = false;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ClientFormModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { client?: Client }
  ) {}

  ngOnInit(): void {
    this.isEdit = !!this.data?.client;

    this.form = this.fb.group({
      nom: [this.data?.client?.nom || '', Validators.required],
      email: [this.data?.client?.email || '', [Validators.required, Validators.email]],
      telephone: [this.data?.client?.telephone || '', Validators.required],
      dateNaissance: [this.data?.client?.dateNaissance || '', Validators.required],
      adresse: [this.data?.client?.adresse || '', Validators.required],
    });
  }

  save() {
    if (this.form.invalid) return;
    const client: Client = { ...this.data?.client, ...this.form.value };
    this.dialogRef.close(client); // on renvoie le client au parent
  }

  cancel() {
    this.dialogRef.close();
  }
}
