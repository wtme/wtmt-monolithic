import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeDetailComponent } from 'app/entities/overtime/overtime-detail.component';
import { Overtime } from 'app/shared/model/overtime.model';

describe('Component Tests', () => {
  describe('Overtime Management Detail Component', () => {
    let comp: OvertimeDetailComponent;
    let fixture: ComponentFixture<OvertimeDetailComponent>;
    const route = ({ data: of({ overtime: new Overtime(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OvertimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OvertimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.overtime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
