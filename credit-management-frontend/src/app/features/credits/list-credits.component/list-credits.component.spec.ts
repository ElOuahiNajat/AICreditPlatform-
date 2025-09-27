import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListCreditsComponent } from './list-credits.component';

describe('ListCreditsComponent', () => {
  let component: ListCreditsComponent;
  let fixture: ComponentFixture<ListCreditsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListCreditsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListCreditsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
