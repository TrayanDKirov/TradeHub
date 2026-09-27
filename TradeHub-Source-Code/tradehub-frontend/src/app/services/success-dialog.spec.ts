import { TestBed } from '@angular/core/testing';

import { SuccessDialog } from './success-dialog';

describe('SuccessDialog', () => {
  let service: SuccessDialog;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SuccessDialog);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
