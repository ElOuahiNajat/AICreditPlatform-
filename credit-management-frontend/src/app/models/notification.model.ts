export interface Notification {
  id: number;
  clientId: number;
  message: string;
  sent: boolean;
  createdAt?: string; // ISO date string
  updatedAt?: string; // ISO date string
}
