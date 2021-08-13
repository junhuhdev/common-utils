package com.tele2.orderflow.sfui.backend.common;

import com.tele2.orderflow.sfui.backend.component.family.model.SubscriptionStatus;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class DateUtils {

	private static final DateTimeFormatter ISO_LOCAL_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

	private DateUtils() {
	}

	// Expected input 2020-10-15T08:05:56Z
	public static LocalDate fromUTC(String val) {
		return LocalDate.ofInstant(Instant.parse(val), ZoneOffset.UTC);
	}

	public static Optional<LocalDate> fromISO(String val) {
		try {
			return Optional.of(LocalDate.parse(val, ISO_LOCAL_DATE_TIME_FORMAT));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static boolean isActiveToday(LocalDate from, LocalDate to) {
		if (isNull(from)) {
			return false;
		}
		var today = LocalDate.now();
		if (from.isBefore(today) || from.isEqual(today)) {
			if (isNull(to)) {
				return true;
			}
			return to.isAfter(today) || to.isEqual(today);
		}
		return false;
	}

	public static boolean isActiveToday(String from, String to) {
		if (isBlank(from)) {
			return false;
		}
		var fromDate = LocalDate.parse(from);
		var today = LocalDate.now();
		if (fromDate.isBefore(today) || fromDate.isEqual(today)) {
			if (isBlank(to)) {
				return true;
			}
			var toDate = LocalDate.parse(to);
			return toDate.isAfter(today) || toDate.isEqual(today);
		}
		return false;
	}

	public static boolean isActiveTodayOrFuture(LocalDate from, LocalDate to) {
		if (isNull(from)) {
			return false;
		}
		var today = LocalDate.now();
		if (from.isAfter(today) || from.isEqual(today)) {
			if (isNull(to)) {
				return true;
			}
			return to.isAfter(today) || to.isEqual(today);
		}
		return false;
	}

	public static boolean isActiveTodayOrFuture(String from, String to) {
		if (isBlank(from)) {
			return false;
		}
		var fromDate = LocalDate.parse(from);
		var today = LocalDate.now();
		if (fromDate.isAfter(today) || fromDate.isEqual(today)) {
			if (isBlank(to)) {
				return true;
			}
			var toDate = LocalDate.parse(to);
			return toDate.isAfter(today) || toDate.isEqual(today);
		}
		return false;
	}

	public static SubscriptionStatus parseSubscriptionStatus(LocalDate from, LocalDate to) {
		if (isNull(from)) {
			return SubscriptionStatus.INACTIVE;
		}
		var today = LocalDate.now();
		if (from.isBefore(today) || from.isEqual(today)) {
			if (isNull(to)) {
				return SubscriptionStatus.ACTIVE;
			}
			return (to.isAfter(today) || to.isEqual(today)) ? SubscriptionStatus.ACTIVE : SubscriptionStatus.INACTIVE;
		}
		return SubscriptionStatus.PENDING;
	}

	public static SubscriptionStatus parseSubscriptionStatus(String from, String to) {
		if (isBlank(from)) {
			return SubscriptionStatus.INACTIVE;
		}
		var fromDate = LocalDate.parse(from);
		var today = LocalDate.now();
		if (fromDate.isBefore(today) || fromDate.isEqual(today)) {
			if (isBlank(to)) {
				return SubscriptionStatus.ACTIVE;
			}
			var toDate = LocalDate.parse(to);
			if (toDate.isBefore(today)) {
				return SubscriptionStatus.INACTIVE;
			}
			return (toDate.isAfter(today) || toDate.isEqual(today)) ? SubscriptionStatus.ACTIVE : SubscriptionStatus.PENDING;
		}
		return SubscriptionStatus.PENDING;
	}

	public static Optional<LocalDate> parseLocalDate(String date) {
		return Optional.ofNullable(date)
				.filter(StringUtils::isNotBlank)
				.map(LocalDate::parse);
	}

	public static LocalDate localDateOrNull(String date) {
		return parseLocalDate(date)
				.orElse(null);
	}

}
