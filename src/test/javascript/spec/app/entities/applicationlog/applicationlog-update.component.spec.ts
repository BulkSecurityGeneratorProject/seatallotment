/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { SeatAllotmentTestModule } from '../../../test.module';
import { ApplicationlogUpdateComponent } from 'app/entities/applicationlog/applicationlog-update.component';
import { ApplicationlogService } from 'app/entities/applicationlog/applicationlog.service';
import { Applicationlog } from 'app/shared/model/applicationlog.model';

describe('Component Tests', () => {
  describe('Applicationlog Management Update Component', () => {
    let comp: ApplicationlogUpdateComponent;
    let fixture: ComponentFixture<ApplicationlogUpdateComponent>;
    let service: ApplicationlogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SeatAllotmentTestModule],
        declarations: [ApplicationlogUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationlogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationlogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationlogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Applicationlog('9fec3727-3421-4967-b213-ba36557ca194');
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
        const entity = new Applicationlog();
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