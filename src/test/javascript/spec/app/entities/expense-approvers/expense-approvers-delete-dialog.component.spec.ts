import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WmteTestModule } from '../../../test.module';
import { ExpenseApproversDeleteDialogComponent } from 'app/entities/expense-approvers/expense-approvers-delete-dialog.component';
import { ExpenseApproversService } from 'app/entities/expense-approvers/expense-approvers.service';

describe('Component Tests', () => {
  describe('ExpenseApprovers Management Delete Component', () => {
    let comp: ExpenseApproversDeleteDialogComponent;
    let fixture: ComponentFixture<ExpenseApproversDeleteDialogComponent>;
    let service: ExpenseApproversService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [ExpenseApproversDeleteDialogComponent]
      })
        .overrideTemplate(ExpenseApproversDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpenseApproversDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpenseApproversService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
