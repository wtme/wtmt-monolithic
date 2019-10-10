import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeCommentDetailComponent } from 'app/entities/overtime-comment/overtime-comment-detail.component';
import { OvertimeComment } from 'app/shared/model/overtime-comment.model';

describe('Component Tests', () => {
  describe('OvertimeComment Management Detail Component', () => {
    let comp: OvertimeCommentDetailComponent;
    let fixture: ComponentFixture<OvertimeCommentDetailComponent>;
    const route = ({ data: of({ overtimeComment: new OvertimeComment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeCommentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OvertimeCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OvertimeCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.overtimeComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
