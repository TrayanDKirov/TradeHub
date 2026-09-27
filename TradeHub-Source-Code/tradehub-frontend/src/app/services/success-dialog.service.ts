import { Injectable } from '@angular/core';
import { SuccessDialogComponent } from '../components/dialog/success-dialog/success-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { MessageResponseDto } from '../dtos/message.dto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  constructor(private dialog: MatDialog) {}

  showSuccess(messageDto: MessageResponseDto) {
    let message = "";
    if (environment.shouldShowUserMessages) {
      message = messageDto.userMessage;
    }
    else {
      message = messageDto.developerMessage;
    }

    this.dialog.open(SuccessDialogComponent, { data: { message } });
  }
}