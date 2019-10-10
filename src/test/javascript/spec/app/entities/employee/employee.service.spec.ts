import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

describe('Service Tests', () => {
  describe('Employee Service', () => {
    let injector: TestBed;
    let service: EmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmployee;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EmployeeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Employee(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        Gender.MALE,
        EmployeeStatus.ACTIVE,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfJoining: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Employee', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfJoining: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfJoining: currentDate,
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .create(new Employee(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Employee', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            fullname: 'BBBBBB',
            login: 'BBBBBB',
            email: 'BBBBBB',
            personalEmail: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            mobile: 'BBBBBB',
            dateOfJoining: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            status: 'BBBBBB',
            employeeNumber: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
            note: 'BBBBBB',
            userId: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfJoining: currentDate,
            dateOfBirth: currentDate
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

      it('should return a list of Employee', () => {
        const returnedFromService = Object.assign(
          {
            code: 'BBBBBB',
            fullname: 'BBBBBB',
            login: 'BBBBBB',
            email: 'BBBBBB',
            personalEmail: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            mobile: 'BBBBBB',
            dateOfJoining: currentDate.format(DATE_TIME_FORMAT),
            gender: 'BBBBBB',
            status: 'BBBBBB',
            employeeNumber: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_TIME_FORMAT),
            note: 'BBBBBB',
            userId: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfJoining: currentDate,
            dateOfBirth: currentDate
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

      it('should delete a Employee', () => {
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
