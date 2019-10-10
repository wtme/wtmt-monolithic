import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { DepartmentApproveUpdateComponent } from 'app/entities/department-approve/department-approve-update.component';
import { DepartmentApproveService } from 'app/entities/department-approve/department-approve.service';
import { DepartmentApprove } from 'app/shared/model/department-approve.model';

describe('Component Tests', () => {
  describe('DepartmentApprove Management Update Component', () => {
    let comp: DepartmentApproveUpdateComponent;
    let fixture: ComponentFixture<DepartmentApproveUpdateComponent>;
    let service: DepartmentApproveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [DepartmentApproveUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepartmentApproveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartmentApproveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentApproveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepartmentApprove(123);
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
        const entity = new DepartmentApprove();
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
