import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { EmployeeHasRoleDetailComponent } from 'app/entities/employee-has-role/employee-has-role-detail.component';
import { EmployeeHasRole } from 'app/shared/model/employee-has-role.model';

describe('Component Tests', () => {
  describe('EmployeeHasRole Management Detail Component', () => {
    let comp: EmployeeHasRoleDetailComponent;
    let fixture: ComponentFixture<EmployeeHasRoleDetailComponent>;
    const route = ({ data: of({ employeeHasRole: new EmployeeHasRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [EmployeeHasRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmployeeHasRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeHasRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeHasRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
