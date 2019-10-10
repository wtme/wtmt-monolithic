import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeCommentUpdateComponent } from 'app/entities/overtime-comment/overtime-comment-update.component';
import { OvertimeCommentService } from 'app/entities/overtime-comment/overtime-comment.service';
import { OvertimeComment } from 'app/shared/model/overtime-comment.model';

describe('Component Tests', () => {
  describe('OvertimeComment Management Update Component', () => {
    let comp: OvertimeCommentUpdateComponent;
    let fixture: ComponentFixture<OvertimeCommentUpdateComponent>;
    let service: OvertimeCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeCommentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OvertimeCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OvertimeCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OvertimeCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OvertimeComment(123);
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
        const entity = new OvertimeComment();
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
