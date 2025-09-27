import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {ChatService} from '../../core/services/chat.service';

interface Message {
  from: 'user' | 'bot';
  text: string;
}

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './chat.component.html',
})
export class ChatComponent {
  question: string = '';
  messages: Message[] = [];

  constructor(private chatService: ChatService) {}

  sendMessage() {
    if (!this.question.trim()) return;

    const userMsg: Message = { from: 'user', text: this.question };
    this.messages.push(userMsg);

    this.chatService.sendQuestion(this.question).subscribe({
      next: (res) => {
        const botMsg: Message = { from: 'bot', text: res.response };
        this.messages.push(botMsg);
      },
      error: (err) => {
        const botMsg: Message = { from: 'bot', text: 'Erreur: impossible de contacter le serveur' };
        this.messages.push(botMsg);
      },
    });

    this.question = '';
  }
}
