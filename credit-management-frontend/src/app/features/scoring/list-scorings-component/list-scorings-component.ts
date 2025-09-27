import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Scoring, ScoringService } from '../../../core/services/scoring.service';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ScoringFormModalComponent } from '../scoring-form-modal/scoring-form-modal.component';

@Component({
  selector: 'app-list-scorings',
  standalone: true,
  imports: [CommonModule, NgbModule, FormsModule],
  templateUrl: './list-scorings-component.html'
})
export class ListScoringsComponent {
  scorings: Scoring[] = [];
  clientIdFilter: number = 1;

  constructor(private scoringService: ScoringService, private modalService: NgbModal) {
    this.loadHistory(this.clientIdFilter);
  }

  loadHistory(clientId: number) {
    this.scoringService.getHistory(clientId).subscribe(res => this.scorings = res);
  }

  openAddModal() {
    const modalRef = this.modalService.open(ScoringFormModalComponent, { centered: true, size: 'lg' });
    modalRef.result.then(res => {
      if (res === 'saved') this.loadHistory(this.clientIdFilter);
    }).catch(() => {});
  }

  updateStatus(scoring: Scoring, status: string) {
    if (!scoring.id) return;
    this.scoringService.updateStatus(scoring.id, status).subscribe({
      next: () => this.loadHistory(this.clientIdFilter),
      error: err => console.error(err)
    });
  }

  deleteScoring(scoring: Scoring) {
    if (!scoring.id) return;
    if (confirm('Êtes-vous sûr de vouloir supprimer ce scoring ?')) {
      this.scoringService.deleteScoring(scoring.id).subscribe(() => this.loadHistory(this.clientIdFilter));
    }
  }
}
