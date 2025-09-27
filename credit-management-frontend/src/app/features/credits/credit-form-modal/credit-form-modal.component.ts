import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { Credit, CreditStatus } from '../../../models/credit.model';
import { CreditService } from '../../../core/services/credit.service';

@Component({
  selector: 'app-credit-form-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './credit-form-modal.component.html',
  styleUrls: ['./credit-form-modal.component.css']
})
export class CreditFormModalComponent {
  creditForm: FormGroup;
  credit?: Credit;

  constructor(
    public activeModal: NgbActiveModal,
    private fb: FormBuilder,
    private creditService: CreditService
  ) {
    this.creditForm = this.fb.group({
      clientId: [null, Validators.required],
      montant: [null, Validators.required],
      dureeMois: [null, Validators.required],
    });
  }

  ngOnInit() {
    if (this.credit) this.creditForm.patchValue(this.credit);
  }

  saveCredit() {
    if (this.creditForm.invalid) return;

    const data: Credit = this.creditForm.value;
    if (this.credit && this.credit.id) data.id = this.credit.id;

    const obs = this.credit ? this.creditService.updateCredit(data) : this.creditService.createCredit(data);
    obs.subscribe(() => this.activeModal.close('saved'));
  }

  close() {
    this.activeModal.dismiss();
  }
}
