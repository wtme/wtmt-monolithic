import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WmteTestModule } from '../../../test.module';
import { OvertimeApprovalDeleteDialogComponent } from 'app/entities/overtime-approval/overtime-approval-delete-dialog.component';
import { OvertimeApprovalService } from 'app/entities/overtime-approval/overtime-approval.service';

describe('Component Tests', () => {
  describe('OvertimeApproval Management Delete Component', () => {
    let comp: OvertimeApprovalDeleteDialogComponent;
    let fixture: ComponentFixture<OvertimeApprovalDeleteDialogComponent>;
    let service: OvertimeApprovalService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeApprovalDeleteDialogComponent]
      })
        .overrideTemplate(OvertimeApprovalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OvertimeApprovalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OvertimeApprovalService);
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
