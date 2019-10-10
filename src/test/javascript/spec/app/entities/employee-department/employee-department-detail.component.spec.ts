import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { EmployeeDepartmentDetailComponent } from 'app/entities/employee-department/employee-department-detail.component';
import { EmployeeDepartment } from 'app/shared/model/employee-department.model';

describe('Component Tests', () => {
  describe('EmployeeDepartment Management Detail Component', () => {
    let comp: EmployeeDepartmentDetailComponent;
    let fixture: ComponentFixture<EmployeeDepartmentDetailComponent>;
    const route = ({ data: of({ employeeDepartment: new EmployeeDepartment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [EmployeeDepartmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EmployeeDepartmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeDepartmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.employeeDepartment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
