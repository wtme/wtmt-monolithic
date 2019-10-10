import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WmteTestModule } from '../../../test.module';
import { OvertimeDeleteDialogComponent } from 'app/entities/overtime/overtime-delete-dialog.component';
import { OvertimeService } from 'app/entities/overtime/overtime.service';

describe('Component Tests', () => {
  describe('Overtime Management Delete Component', () => {
    let comp: OvertimeDeleteDialogComponent;
    let fixture: ComponentFixture<OvertimeDeleteDialogComponent>;
    let service: OvertimeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeDeleteDialogComponent]
      })
        .overrideTemplate(OvertimeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OvertimeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OvertimeService);
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
