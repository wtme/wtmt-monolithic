import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { ExpenseApproversDetailComponent } from 'app/entities/expense-approvers/expense-approvers-detail.component';
import { ExpenseApprovers } from 'app/shared/model/expense-approvers.model';

describe('Component Tests', () => {
  describe('ExpenseApprovers Management Detail Component', () => {
    let comp: ExpenseApproversDetailComponent;
    let fixture: ComponentFixture<ExpenseApproversDetailComponent>;
    const route = ({ data: of({ expenseApprovers: new ExpenseApprovers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [ExpenseApproversDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExpenseApproversDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpenseApproversDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expenseApprovers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
