import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListScoringsComponent } from './list-scorings-component';

describe('ListScoringsComponent', () => {
  let component: ListScoringsComponent;
  let fixture: ComponentFixture<ListScoringsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListScoringsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListScoringsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
