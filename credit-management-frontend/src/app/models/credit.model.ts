export enum CreditStatus {
  EN_ATTENTE = 'EN_ATTENTE',
  APPROUVE = 'APPROUVE',
  REFUSE = 'REFUSE',
  REMBOURSE = 'REMBOURSE'
}

export interface Credit {
  id?: number;
  clientId: number;
  montant: number;
  dureeMois: number;
  dateDemande?: string;
  dateApprobation?: string;
  statut?: CreditStatus;
  montantRembourse?: number;
}
