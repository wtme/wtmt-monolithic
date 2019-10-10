import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeHistoryDetailComponent } from 'app/entities/overtime-history/overtime-history-detail.component';
import { OvertimeHistory } from 'app/shared/model/overtime-history.model';

describe('Component Tests', () => {
  describe('OvertimeHistory Management Detail Component', () => {
    let comp: OvertimeHistoryDetailComponent;
    let fixture: ComponentFixture<OvertimeHistoryDetailComponent>;
    const route = ({ data: of({ overtimeHistory: new OvertimeHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OvertimeHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OvertimeHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.overtimeHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
