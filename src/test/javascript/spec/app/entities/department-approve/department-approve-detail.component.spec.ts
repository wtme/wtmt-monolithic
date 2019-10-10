import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { DepartmentApproveDetailComponent } from 'app/entities/department-approve/department-approve-detail.component';
import { DepartmentApprove } from 'app/shared/model/department-approve.model';

describe('Component Tests', () => {
  describe('DepartmentApprove Management Detail Component', () => {
    let comp: DepartmentApproveDetailComponent;
    let fixture: ComponentFixture<DepartmentApproveDetailComponent>;
    const route = ({ data: of({ departmentApprove: new DepartmentApprove(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [DepartmentApproveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DepartmentApproveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentApproveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.departmentApprove).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
