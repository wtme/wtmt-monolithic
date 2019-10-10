import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeApprovalUpdateComponent } from 'app/entities/overtime-approval/overtime-approval-update.component';
import { OvertimeApprovalService } from 'app/entities/overtime-approval/overtime-approval.service';
import { OvertimeApproval } from 'app/shared/model/overtime-approval.model';

describe('Component Tests', () => {
  describe('OvertimeApproval Management Update Component', () => {
    let comp: OvertimeApprovalUpdateComponent;
    let fixture: ComponentFixture<OvertimeApprovalUpdateComponent>;
    let service: OvertimeApprovalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeApprovalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OvertimeApprovalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OvertimeApprovalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OvertimeApprovalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OvertimeApproval(123);
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
        const entity = new OvertimeApproval();
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
