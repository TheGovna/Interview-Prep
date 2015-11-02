// Source: http://www.programcreek.com/2012/11/quicksort-array-in-java/
void quickSort(int[] a, int low, int high) {
	if (a == null || a.length == 0) {
		return;
	}

	if (low >= high) {
		return;
	}

	// Pick the pivot
	int middle = low + (high - low) / 2;
	int pivot = a[middle];

	// Make left < pivot and right > pivot
	int i = low, j = high;
	while (i <= j) {
		while (a[i] < pivot) {
			i++;
		}

		while (a[j] > pivot) {
			j--;
		}

		if (i <= j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
			i++;
			j--;
		}
	}

	// Recursively sort two sub parts
	if (low < j) {
		quickSort(a, low, j);
	}

	if (high > i) {
		quickSort(a, i, high);
	}
}