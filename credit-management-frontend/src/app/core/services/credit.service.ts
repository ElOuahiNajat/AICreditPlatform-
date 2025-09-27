import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Credit, CreditStatus } from '../../models/credit.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CreditService {
  private api = 'http://localhost:8081/api/credits';

  constructor(private http: HttpClient) {}

  getCredits(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.api}?page=${page}&size=${size}`);
  }

  createCredit(credit: Credit): Observable<Credit> {
    return this.http.post<Credit>(`${this.api}/demandes`, credit);
  }

  updateCredit(credit: Credit): Observable<Credit> {
    return this.http.put<Credit>(`${this.api}/demandes/${credit.id}`, credit);
  }

  deleteCredit(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/demandes/${id}`);
  }

  updateCreditStatus(id: number, statut: CreditStatus): Observable<Credit> {
    return this.http.patch<Credit>(`${this.api}/demandes/${id}/status?statut=${statut}`, {});
  }

  exportCSV(): Observable<Blob> {
    return this.http.get(`${this.api}/export`, { responseType: 'blob' });
  }
}
