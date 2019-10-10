import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { DepartmentRoleUpdateComponent } from 'app/entities/department-role/department-role-update.component';
import { DepartmentRoleService } from 'app/entities/department-role/department-role.service';
import { DepartmentRole } from 'app/shared/model/department-role.model';

describe('Component Tests', () => {
  describe('DepartmentRole Management Update Component', () => {
    let comp: DepartmentRoleUpdateComponent;
    let fixture: ComponentFixture<DepartmentRoleUpdateComponent>;
    let service: DepartmentRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [DepartmentRoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DepartmentRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartmentRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepartmentRole(123);
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
        const entity = new DepartmentRole();
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
