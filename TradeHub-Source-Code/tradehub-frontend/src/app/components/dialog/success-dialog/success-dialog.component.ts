import { Component, Inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@Component({
  selector: 'app-success-dialog',
  imports: [MatProgressBarModule],
  templateUrl: './success-dialog.component.html',
  styleUrls: ['./success-dialog.component.css']
})
export class SuccessDialogComponent implements OnInit {
  progress = 0;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { message: string },
    private dialogRef: MatDialogRef<SuccessDialogComponent>,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const duration = 3000; // 3 seconds
    const intervalTime = 100; // update every 100ms
    const step = (intervalTime / duration) * 100;

    const interval = setInterval(() => {
      this.progress += step;
      this.cdr.detectChanges(); // force Angular to check the updated progress
      if (this.progress >= 100) {
        clearInterval(interval);
        this.dialogRef.close();
      }
    }, intervalTime);
  }
}
