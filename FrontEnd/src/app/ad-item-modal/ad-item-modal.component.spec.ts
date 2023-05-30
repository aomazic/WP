import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdItemModalComponent } from './ad-item-modal.component';

describe('AdItemModalComponent', () => {
  let component: AdItemModalComponent;
  let fixture: ComponentFixture<AdItemModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AdItemModalComponent]
    });
    fixture = TestBed.createComponent(AdItemModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
