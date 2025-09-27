// chat.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private apiUrl = 'http://localhost:5000/chat';

  constructor(private http: HttpClient) {}

  sendQuestion(question: string): Observable<{question: string, response: string}> {
    return this.http.post<{question: string, response: string}>(this.apiUrl, { question });
  }
}
