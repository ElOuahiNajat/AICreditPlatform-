import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ScoringRequest, ScoringService } from '../../../core/services/scoring.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-scoring-form-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './scoring-form-modal.component.html'
})
export class ScoringFormModalComponent {
  clientId!: number;
  creditId!: number;
  revenus!: number;
  dettes!: number;

  constructor(public activeModal: NgbActiveModal, private scoringService: ScoringService) {}

  save() {
    const request: ScoringRequest = {
      clientId: this.clientId,
      creditId: this.creditId,
      revenus: this.revenus,
      dettes: this.dettes
    };
    this.scoringService.calculateScore(request).subscribe(() => this.activeModal.close('saved'));
  }
}
