import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeApprovalDetailComponent } from 'app/entities/overtime-approval/overtime-approval-detail.component';
import { OvertimeApproval } from 'app/shared/model/overtime-approval.model';

describe('Component Tests', () => {
  describe('OvertimeApproval Management Detail Component', () => {
    let comp: OvertimeApprovalDetailComponent;
    let fixture: ComponentFixture<OvertimeApprovalDetailComponent>;
    const route = ({ data: of({ overtimeApproval: new OvertimeApproval(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeApprovalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OvertimeApprovalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OvertimeApprovalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.overtimeApproval).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
