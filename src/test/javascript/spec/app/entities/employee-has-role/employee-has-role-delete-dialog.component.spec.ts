import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WmteTestModule } from '../../../test.module';
import { EmployeeHasRoleDeleteDialogComponent } from 'app/entities/employee-has-role/employee-has-role-delete-dialog.component';
import { EmployeeHasRoleService } from 'app/entities/employee-has-role/employee-has-role.service';

describe('Component Tests', () => {
  describe('EmployeeHasRole Management Delete Component', () => {
    let comp: EmployeeHasRoleDeleteDialogComponent;
    let fixture: ComponentFixture<EmployeeHasRoleDeleteDialogComponent>;
    let service: EmployeeHasRoleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [EmployeeHasRoleDeleteDialogComponent]
      })
        .overrideTemplate(EmployeeHasRoleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeHasRoleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeHasRoleService);
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
