import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Notification } from '../../../models/notification.model';
import { NotificationService } from '../../../core/services/notification.service';

@Component({
  selector: 'app-notification-form-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgbModule],
  templateUrl: './notification-form-modal.component.html',
  styleUrls: ['./notification-form-modal.component.css']
})
export class NotificationFormModalComponent {
  notificationForm: FormGroup;
  notification?: Notification;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private notificationService: NotificationService
  ) {
    this.notificationForm = this.fb.group({
      clientId: [null, Validators.required],
      message: ['', Validators.required],
    });
  }

  ngOnInit() {
    if (this.notification) {
      this.notificationForm.patchValue(this.notification);
    }
  }

  save() {
    if (this.notificationForm.invalid) return;

    const { clientId, message } = this.notificationForm.value;

    let obs;
    if (this.notification) {
      // Ici on mettrait une vraie méthode update si le backend la gérait
      obs = this.notificationService.updateStatus(this.notification.id!, true);
    } else {
      obs = this.notificationService.create(clientId, message);
    }

    obs.subscribe(() => this.activeModal.close('saved'));
  }

  close() {
    this.activeModal.dismiss();
  }
}
