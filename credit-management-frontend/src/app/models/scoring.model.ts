export type ScoringStatus = 'EN_COURS' | 'APPROUVE' | 'REFUSE';

export interface Scoring {
  id: number;
  clientId: number;
  creditId: number;
  score: number;
  statut: ScoringStatus;
}
