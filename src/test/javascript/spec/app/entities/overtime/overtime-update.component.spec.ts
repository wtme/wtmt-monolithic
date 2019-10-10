import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WmteTestModule } from '../../../test.module';
import { OvertimeUpdateComponent } from 'app/entities/overtime/overtime-update.component';
import { OvertimeService } from 'app/entities/overtime/overtime.service';
import { Overtime } from 'app/shared/model/overtime.model';

describe('Component Tests', () => {
  describe('Overtime Management Update Component', () => {
    let comp: OvertimeUpdateComponent;
    let fixture: ComponentFixture<OvertimeUpdateComponent>;
    let service: OvertimeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WmteTestModule],
        declarations: [OvertimeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OvertimeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OvertimeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OvertimeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Overtime(123);
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
        const entity = new Overtime();
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
