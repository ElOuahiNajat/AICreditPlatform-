import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Credit, CreditStatus } from '../../../models/credit.model';
import { CreditService } from '../../../core/services/credit.service';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CreditFormModalComponent } from '../credit-form-modal/credit-form-modal.component';

@Component({
  selector: 'app-list-credits',
  standalone: true,
  imports: [CommonModule, NgbModule],
  templateUrl: './list-credits.component.html',
})
export class ListCreditsComponent {
  credits: Credit[] = [];
  page = 0;
  size = 10;

  constructor(private creditService: CreditService, private modalService: NgbModal) {
    this.loadCredits();
  }

  loadCredits() {
    this.creditService.getCredits(this.page, this.size).subscribe(res => (this.credits = res.content));
  }

  openAddModal(credit?: Credit) {
    const modalRef = this.modalService.open(CreditFormModalComponent, {
      centered: true, // ✅ centré verticalement
      backdrop: 'static',
      keyboard: false,
    });

    if (credit) modalRef.componentInstance.credit = { ...credit };

    modalRef.result
      .then(result => { if (result === 'saved') this.loadCredits(); })
      .catch(() => {});
  }
  editCredit(credit: Credit) { this.openAddModal(credit); }
  deleteCredit(credit: Credit) {
    if (confirm('Confirmer la suppression ?')) this.creditService.deleteCredit(credit.id!).subscribe(() => this.loadCredits());
  }

  approveCredit(credit: Credit) {
    this.creditService.updateCreditStatus(credit.id!, CreditStatus.APPROUVE).subscribe(() => this.loadCredits());
  }

  refuseCredit(credit: Credit) {
    this.creditService.updateCreditStatus(credit.id!, CreditStatus.REFUSE).subscribe(() => this.loadCredits());
  }

  prevPage() { if (this.page > 0) { this.page--; this.loadCredits(); } }
  nextPage() { this.page++; this.loadCredits(); }

  exportCSV() {
    this.creditService.exportCSV().subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'credits.csv';
      a.click();
      window.URL.revokeObjectURL(url);
    });
  }
}
