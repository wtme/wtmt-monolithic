import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WmteTestModule } from '../../../test.module';
import { DepartmentApproveDeleteDialogComponent } from 'app/entities/department-approve/department-approve-delete-dialog.component';
import { DepartmentApproveService } from 'app/entities/department-approve/department-approve.service';

describe('Component Tests', () => {
  describe('DepartmentApprove Management Delete Component', () => {
    let comp: DepartmentApproveDeleteDialogComponent;
    let fixture: ComponentFixture<DepartmentApproveDeleteDialogComponent>;
    let service: DepartmentApproveService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [DepartmentApproveDeleteDialogComponent]
      })
        .overrideTemplate(DepartmentApproveDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentApproveDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentApproveService);
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
