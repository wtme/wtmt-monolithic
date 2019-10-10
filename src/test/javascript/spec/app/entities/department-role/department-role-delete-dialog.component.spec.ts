import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WmteTestModule } from '../../../test.module';
import { DepartmentRoleDeleteDialogComponent } from 'app/entities/department-role/department-role-delete-dialog.component';
import { DepartmentRoleService } from 'app/entities/department-role/department-role.service';

describe('Component Tests', () => {
  describe('DepartmentRole Management Delete Component', () => {
    let comp: DepartmentRoleDeleteDialogComponent;
    let fixture: ComponentFixture<DepartmentRoleDeleteDialogComponent>;
    let service: DepartmentRoleService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [DepartmentRoleDeleteDialogComponent]
      })
        .overrideTemplate(DepartmentRoleDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentRoleDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentRoleService);
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
