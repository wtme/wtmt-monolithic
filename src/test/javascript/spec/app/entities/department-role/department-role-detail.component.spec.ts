import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { DepartmentRoleDetailComponent } from 'app/entities/department-role/department-role-detail.component';
import { DepartmentRole } from 'app/shared/model/department-role.model';

describe('Component Tests', () => {
  describe('DepartmentRole Management Detail Component', () => {
    let comp: DepartmentRoleDetailComponent;
    let fixture: ComponentFixture<DepartmentRoleDetailComponent>;
    const route = ({ data: of({ departmentRole: new DepartmentRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [DepartmentRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepartmentRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.departmentRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
