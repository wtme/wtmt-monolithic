import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { EmployeeHasRoleUpdateComponent } from 'app/entities/employee-has-role/employee-has-role-update.component';
import { EmployeeHasRoleService } from 'app/entities/employee-has-role/employee-has-role.service';
import { EmployeeHasRole } from 'app/shared/model/employee-has-role.model';

describe('Component Tests', () => {
  describe('EmployeeHasRole Management Update Component', () => {
    let comp: EmployeeHasRoleUpdateComponent;
    let fixture: ComponentFixture<EmployeeHasRoleUpdateComponent>;
    let service: EmployeeHasRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [EmployeeHasRoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmployeeHasRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeHasRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeHasRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeHasRole(123);
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
        const entity = new EmployeeHasRole();
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
