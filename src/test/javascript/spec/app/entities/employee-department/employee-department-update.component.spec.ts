import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { EmployeeDepartmentUpdateComponent } from 'app/entities/employee-department/employee-department-update.component';
import { EmployeeDepartmentService } from 'app/entities/employee-department/employee-department.service';
import { EmployeeDepartment } from 'app/shared/model/employee-department.model';

describe('Component Tests', () => {
  describe('EmployeeDepartment Management Update Component', () => {
    let comp: EmployeeDepartmentUpdateComponent;
    let fixture: ComponentFixture<EmployeeDepartmentUpdateComponent>;
    let service: EmployeeDepartmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [EmployeeDepartmentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmployeeDepartmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeDepartmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeDepartmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeDepartment(123);
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
        const entity = new EmployeeDepartment();
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
