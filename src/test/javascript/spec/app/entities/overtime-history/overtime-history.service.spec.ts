import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OvertimeHistoryService } from 'app/entities/overtime-history/overtime-history.service';
import { IOvertimeHistory, OvertimeHistory } from 'app/shared/model/overtime-history.model';
import { OvertimeStatus } from 'app/shared/model/enumerations/overtime-status.model';

describe('Service Tests', () => {
  describe('OvertimeHistory Service', () => {
    let injector: TestBed;
    let service: OvertimeHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: IOvertimeHistory;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(OvertimeHistoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OvertimeHistory(0, currentDate, OvertimeStatus.OPEN, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a OvertimeHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            date: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .create(new OvertimeHistory(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a OvertimeHistory', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            changeType: 'BBBBBB',
            who: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of OvertimeHistory', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            changeType: 'BBBBBB',
            who: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OvertimeHistory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
