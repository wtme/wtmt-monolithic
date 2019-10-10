import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { ExpenseApproversUpdateComponent } from 'app/entities/expense-approvers/expense-approvers-update.component';
import { ExpenseApproversService } from 'app/entities/expense-approvers/expense-approvers.service';
import { ExpenseApprovers } from 'app/shared/model/expense-approvers.model';

describe('Component Tests', () => {
  describe('ExpenseApprovers Management Update Component', () => {
    let comp: ExpenseApproversUpdateComponent;
    let fixture: ComponentFixture<ExpenseApproversUpdateComponent>;
    let service: ExpenseApproversService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [ExpenseApproversUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ExpenseApproversUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpenseApproversUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpenseApproversService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpenseApprovers(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpenseApprovers();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
