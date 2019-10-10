import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeHistoryUpdateComponent } from 'app/entities/overtime-history/overtime-history-update.component';
import { OvertimeHistoryService } from 'app/entities/overtime-history/overtime-history.service';
import { OvertimeHistory } from 'app/shared/model/overtime-history.model';

describe('Component Tests', () => {
  describe('OvertimeHistory Management Update Component', () => {
    let comp: OvertimeHistoryUpdateComponent;
    let fixture: ComponentFixture<OvertimeHistoryUpdateComponent>;
    let service: OvertimeHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OvertimeHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OvertimeHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OvertimeHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OvertimeHistory(123);
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
        const entity = new OvertimeHistory();
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
